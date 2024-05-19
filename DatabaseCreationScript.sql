USE [Archi]
GO
/****** Object:  UserDefinedTableType [dbo].[ModelElementTableType]    Script Date: 5/19/2024 7:00:18 PM ******/
CREATE TYPE [dbo].[ModelElementTableType] AS TABLE(
	[Id] [uniqueidentifier] NULL,
	[ModelId] [uniqueidentifier] NULL,
	[ModelVersion] [int] NULL,
	[ParentId] [uniqueidentifier] NULL,
	[ElementType] [nvarchar](250) NULL,
	[ElementContent] [xml] NULL
)
GO
/****** Object:  Table [dbo].[ModelElementHistories]    Script Date: 5/19/2024 7:00:18 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ModelElementHistories](
	[Id] [uniqueidentifier] NOT NULL,
	[ElementId] [uniqueidentifier] NOT NULL,
	[ModelId] [uniqueidentifier] NOT NULL,
	[ModelVersion] [int] NOT NULL,
	[ParentId] [uniqueidentifier] NOT NULL,
	[ModificationType] [nvarchar](50) NOT NULL,
	[Date] [datetime] NOT NULL,
	[UserId] [uniqueidentifier] NOT NULL,
	[XmlContent] [xml] NULL,
 CONSTRAINT [PK_ModelElementHistories] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ModelElements]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ModelElements](
	[Id] [uniqueidentifier] NOT NULL,
	[ModelId] [uniqueidentifier] NOT NULL,
	[ModelVersion] [int] NOT NULL,
	[ParentId] [uniqueidentifier] NOT NULL,
	[ElementType] [nvarchar](250) NOT NULL,
	[ElementContent] [xml] NULL,
	[ElementStatus] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_ModelElements] PRIMARY KEY CLUSTERED 
(
	[Id] ASC,
	[ModelId] ASC,
	[ModelVersion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ModelImages]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ModelImages](
	[Id] [uniqueidentifier] NOT NULL,
	[ModelId] [uniqueidentifier] NOT NULL,
	[ModelVersion] [int] NOT NULL,
	[ImageName] [nvarchar](max) NOT NULL,
	[Image] [image] NOT NULL,
 CONSTRAINT [PK_ModelImages] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Models]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Models](
	[Id] [uniqueidentifier] NOT NULL,
	[ModelName] [nvarchar](250) NOT NULL,
	[CreatedBy] [uniqueidentifier] NOT NULL,
	[CreateDate] [smalldatetime] NOT NULL,
 CONSTRAINT [PK_Models] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ModelVersion]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ModelVersion](
	[ModelId] [uniqueidentifier] NOT NULL,
	[ModelVersion] [int] NOT NULL,
	[IsActive] [bit] NOT NULL,
	[IsLastVersion] [bit] NOT NULL,
	[IsLocked] [bit] NOT NULL,
	[Description] [nvarchar](max) NULL,
	[CreatedDate] [smalldatetime] NOT NULL,
	[CreatedBy] [uniqueidentifier] NOT NULL,
 CONSTRAINT [PK_ModelVersion_1] PRIMARY KEY CLUSTERED 
(
	[ModelId] ASC,
	[ModelVersion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [uniqueidentifier] NOT NULL,
	[FirtName] [nvarchar](250) NOT NULL,
	[LastName] [nvarchar](250) NOT NULL,
	[UserName] [nvarchar](250) NOT NULL,
	[Password] [nvarchar](250) NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ModelElements] ADD  CONSTRAINT [DF_ModelElements_ElementStaus]  DEFAULT ('Active') FOR [ElementStatus]
GO
ALTER TABLE [dbo].[ModelElementHistories]  WITH CHECK ADD  CONSTRAINT [FK_ModelElementHistories_ModelElements] FOREIGN KEY([ElementId], [ModelId], [ModelVersion])
REFERENCES [dbo].[ModelElements] ([Id], [ModelId], [ModelVersion])
GO
ALTER TABLE [dbo].[ModelElementHistories] CHECK CONSTRAINT [FK_ModelElementHistories_ModelElements]
GO
ALTER TABLE [dbo].[ModelElementHistories]  WITH CHECK ADD  CONSTRAINT [FK_ModelElementHistories_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[ModelElementHistories] CHECK CONSTRAINT [FK_ModelElementHistories_Users]
GO
ALTER TABLE [dbo].[ModelElements]  WITH CHECK ADD  CONSTRAINT [FK_ModelElements_ModelVersion] FOREIGN KEY([ModelId], [ModelVersion])
REFERENCES [dbo].[ModelVersion] ([ModelId], [ModelVersion])
GO
ALTER TABLE [dbo].[ModelElements] CHECK CONSTRAINT [FK_ModelElements_ModelVersion]
GO
ALTER TABLE [dbo].[ModelImages]  WITH CHECK ADD  CONSTRAINT [FK_ModelImages_ModelVersion] FOREIGN KEY([ModelId], [ModelVersion])
REFERENCES [dbo].[ModelVersion] ([ModelId], [ModelVersion])
GO
ALTER TABLE [dbo].[ModelImages] CHECK CONSTRAINT [FK_ModelImages_ModelVersion]
GO
ALTER TABLE [dbo].[Models]  WITH CHECK ADD  CONSTRAINT [FK_Models_Users] FOREIGN KEY([CreatedBy])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Models] CHECK CONSTRAINT [FK_Models_Users]
GO
ALTER TABLE [dbo].[ModelVersion]  WITH CHECK ADD  CONSTRAINT [FK_ModelVersion_Models] FOREIGN KEY([ModelId])
REFERENCES [dbo].[Models] ([Id])
GO
ALTER TABLE [dbo].[ModelVersion] CHECK CONSTRAINT [FK_ModelVersion_Models]
GO
ALTER TABLE [dbo].[ModelVersion]  WITH CHECK ADD  CONSTRAINT [FK_ModelVersion_Users] FOREIGN KEY([CreatedBy])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[ModelVersion] CHECK CONSTRAINT [FK_ModelVersion_Users]
GO
/****** Object:  StoredProcedure [dbo].[InsertNewModel]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[InsertNewModel] 
	@ModelId UNIQUEIDENTIFIER,
	@ModelName NVARCHAR(250),
	@UserId UNIQUEIDENTIFIER    

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRANSACTION;
    BEGIN TRY

    INSERT INTO dbo.Models (Id, ModelName, CreatedBy, CreateDate)
    VALUES (@ModelId, @ModelName, @UserId, GETDATE());

	-- Add new version to [dbo].[ModelVersion] 
	INSERT INTO [dbo].[ModelVersion] ([ModelId], [ModelVersion], [IsActive], [IsLastVersion], [IsLocked], [Description], [CreatedDate], [CreatedBy])
	VALUES (@ModelId, 1, 1, 1, 0, 'Initial Version', GETDATE(), @UserId)

	-- Commit the transaction if the INSERT succeeds
    COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Rollback the transaction if an error occurs
        ROLLBACK TRANSACTION;

        -- Rethrow the error to the calling environment
        THROW;
    END CATCH

END
GO
/****** Object:  StoredProcedure [dbo].[ReviseModel]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[ReviseModel] 
	@ModelId UNIQUEIDENTIFIER,
	@ModelVersion int,
	@UserId UNIQUEIDENTIFIER,
	@Description NVARCHAR(MAX)
AS
BEGIN

	SET NOCOUNT ON;

	-- Start the transaction
	BEGIN TRANSACTION;

    BEGIN TRY

		DECLARE @NewVersion int
		SET @NewVersion = @ModelVersion + 1;

		-- Update current version row in [dbo].[ModelVersion]
		UPDATE [dbo].[ModelVersion]
		SET [IsLastVersion] = 0
		WHERE [ModelId] = @ModelId AND [ModelVersion] = @ModelVersion;

		-- Add new version to [dbo].[ModelVersion] 
		INSERT INTO [dbo].[ModelVersion] ([ModelId], [ModelVersion], [IsActive], [IsLastVersion], [IsLocked], [Description], [CreatedDate], [CreatedBy], [UpdatedDate], [UpdateBy])
		VALUES (@ModelId, @NewVersion, 1, 1, 0, @Description, GETDATE(), @UserId, GETDATE(), @UserId)

		-- Add rows to [dbo].[ModelElementHistories] to log this modification
		INSERT INTO [dbo].[ModelElementHistories] ([Id], [ElementId], [ModelId], [ModelVersion], [ModificationType], [Date], [UserId], [XmlContent])
		SELECT NEWID(), [Id], [ModelId], [ModelVersion], 'Revise', GETDATE(), @UserId, [ElementContent]
		FROM [dbo].[ModelElements] WHERE  [ModelId] = @ModelId AND [ModelVersion] = @ModelVersion;

		-- Duplicate elements from old version to new version
		INSERT INTO [dbo].[ModelElements] ([Id], [ModelId], [ModelVersion], [ElementType], [ElementContent])
		SELECT  [Id], [ModelId], @NewVersion, [ElementType], [ElementContent]
		FROM [dbo].[ModelElements] WHERE [ModelId] = @ModelId AND [ModelVersion] = @ModelVersion;

	
        -- Commit the transaction if the INSERT succeeds
        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        -- Rollback the transaction if an error occurs
        ROLLBACK TRANSACTION;

        -- Rethrow the error to the calling environment
        THROW;
    END CATCH

END
GO
/****** Object:  StoredProcedure [dbo].[SaveModelElement]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SaveModelElement]
	@ModelId UNIQUEIDENTIFIER,
	@ModelVersion int,
	@ElementId UNIQUEIDENTIFIER,
	@ParentId UNIQUEIDENTIFIER,
	@ElementType NVARCHAR(50),
	@ElementContent XML NULL,
	@UserId UNIQUEIDENTIFIER,
	@IsDeleted BIT

AS
BEGIN

	SET NOCOUNT ON;

	DECLARE @IsExist BIT
	SELECT @IsExist = CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
	FROM [dbo].[ModelElements] WHERE [Id] = @ElementId AND [ModelId] = @ModelId AND [ModelVersion] = @ModelVersion

	IF @IsExist = 0
		BEGIN
			 INSERT INTO [dbo].[ModelElements] ([Id], [ModelId], [ModelVersion], [ParentId], [ElementType], [ElementContent])
			 VALUES (@ElementId, @ModelId, @ModelVersion, @ParentId, @ElementType, @ElementContent)
		END
	ELSE
		BEGIN
			UPDATE [dbo].[ModelElements]
			SET [ElementContent] = @ElementContent
			FROM [dbo].[ModelElements]
			WHERE [Id] = @ElementId AND [ModelId] = @ModelId AND [ModelVersion] = @ModelVersion
		END

	INSERT INTO [dbo].[ModelElementHistories] ([Id], [ElementId], [ModelId], [ModelVersion], [ParentId], [ModificationType], [Date], [UserId], [XmlContent])
	VALUES
		(
			NEWID(), 
			@ElementId, 
			@ModelId, 
			@ModelVersion,
			@ParentId,
			CASE WHEN @IsExist = 0 THEN 'Create' ELSE CASE WHEN @IsDeleted = 1 THEN 'Delete' ELSE 'Modify' END END , 
			GETDATE(), 
			@UserId, 
			CASE WHEN @IsDeleted = 1 THEN NULL ELSE @ElementContent END
		)

END
GO
/****** Object:  StoredProcedure [dbo].[SaveModelElements]    Script Date: 5/19/2024 7:00:19 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SaveModelElements]
    @ArchiElements [dbo].[ModelElementTableType] READONLY,
    @UserId UNIQUEIDENTIFIER
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRANSACTION;

    BEGIN TRY
        -- Step 1: Update or Insert provided ArchiElements
        DECLARE @Id UNIQUEIDENTIFIER,
                @ModelId UNIQUEIDENTIFIER,
                @ModelVersion INT,
                @ParentId UNIQUEIDENTIFIER,
                @ElementType NVARCHAR(250),
                @ElementContent XML;

        DECLARE ArchiElementsCursor CURSOR FOR
        SELECT 
            Id, 
            ModelId, 
            ModelVersion, 
            ParentId, 
            ElementType, 
            ElementContent 
        FROM @ArchiElements;

        OPEN ArchiElementsCursor;
        FETCH NEXT FROM ArchiElementsCursor INTO @Id, @ModelId, @ModelVersion, @ParentId, @ElementType, @ElementContent;

        WHILE @@FETCH_STATUS = 0
        BEGIN
            -- Check if the element already exists
            IF EXISTS (SELECT 1 FROM dbo.ModelElements WHERE Id = @Id AND ModelId = @ModelId AND ModelVersion = @ModelVersion)
            BEGIN

				IF EXISTS (
							SELECT 1 FROM dbo.ModelElements 
							WHERE Id = @Id AND 
								ModelId = @ModelId AND 
								ModelVersion = @ModelVersion AND
								(ParentId <> @ParentId OR CAST(ElementContent AS NVARCHAR(MAX)) <> CAST(@ElementContent AS NVARCHAR(MAX)))
							)
				BEGIN
					-- Element exists, update it
					UPDATE dbo.ModelElements
					SET 
						ParentId = @ParentId,
						ElementContent = @ElementContent
					WHERE Id = @Id;

						-- Log the update in ModelElementHistories
						INSERT INTO dbo.ModelElementHistories (
							Id, ElementId, ModelId, ModelVersion, ParentId, ModificationType, Date, UserId, XmlContent
						) VALUES (
							NEWID(), @Id, @ModelId, @ModelVersion, @ParentId, 'Updated', GETDATE(), @UserId, @ElementContent
						);
				END
            END
            ELSE
            BEGIN
                -- Element does not exist, insert it
                INSERT INTO dbo.ModelElements (
                    Id, ModelId, ModelVersion, ParentId, ElementType, ElementContent, ElementStatus
                ) VALUES (
                    @Id, @ModelId, @ModelVersion, @ParentId, @ElementType, @ElementContent, 'Active'
                );

                -- Log the creation in ModelElementHistories
                INSERT INTO dbo.ModelElementHistories (
                    Id, ElementId, ModelId, ModelVersion, ParentId, ModificationType, Date, UserId, XmlContent
                ) VALUES (
                    NEWID(), @Id, @ModelId, @ModelVersion, @ParentId, 'Created', GETDATE(), @UserId, @ElementContent
                );
            END

            FETCH NEXT FROM ArchiElementsCursor INTO @Id, @ModelId, @ModelVersion, @ParentId, @ElementType, @ElementContent;
        END

        CLOSE ArchiElementsCursor;
        DEALLOCATE ArchiElementsCursor;

		-- Step 2: Temporary table to handle OUTPUT INTO with foreign key constraints
        CREATE TABLE #DeletedElements (
            ElementId UNIQUEIDENTIFIER,
            ModelId UNIQUEIDENTIFIER,
            ModelVersion INT,
            ParentId UNIQUEIDENTIFIER,
            ModificationType NVARCHAR(50),
            Date DATETIME,
            UserId UNIQUEIDENTIFIER,
            XmlContent XML
        );

		-- Update elements not present in the provided ArchiElements
        UPDATE dbo.ModelElements
        SET 
			ElementStatus = 'Deleted'
        OUTPUT 
            DELETED.Id,
            DELETED.ModelId,
            DELETED.ModelVersion,
            DELETED.ParentId,
            'Deleted',
            GETDATE(),
            @UserId,
            NULL
        INTO #DeletedElements
		WHERE ModelId = @ModelId AND 
			  ModelVersion = @ModelVersion AND 
			  Id NOT IN (SELECT Id FROM @ArchiElements);

		-- Insert from the temporary table into ModelElementHistories
        INSERT INTO dbo.ModelElementHistories (
            Id, ElementId, ModelId, ModelVersion, ParentId, ModificationType, Date, UserId, XmlContent
        )
        SELECT 
            NEWID(), ElementId, ModelId, ModelVersion, ParentId, ModificationType, Date, UserId, XmlContent
        FROM #DeletedElements 
		WHERE (
				SELECT COUNT(*) 
				FROM dbo.ModelElementHistories AS ElmHist 
				WHERE ElmHist.ElementId = #DeletedElements.ElementId AND ElmHist.ModificationType = 'Deleted' AND ElmHist.Date <= #DeletedElements.Date
			  ) = 0;

        -- Drop the temporary table
        DROP TABLE #DeletedElements;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH;
END

GO
